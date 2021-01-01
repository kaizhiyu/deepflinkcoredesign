package chapter08;

import jdk.nashorn.internal.runtime.regexp.joni.constants.StringType;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.sinks.CsvTableSink;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.table.sources.TableSource;
import org.apache.flink.types.Row;

/**
 * create 2021-01-01
 * author zy
 */
public class CsvTableSourceTest {
    private static final TypeInformation[] FIELD_TYPES = new TypeInformation[]{
            BasicTypeInfo.STRING_TYPE_INFO,
            BasicTypeInfo.INT_TYPE_INFO,
            BasicTypeInfo.STRING_TYPE_INFO
    };

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnvironment = TableEnvironment.getTableEnvironment(env);
        String[] fields = {"name","age","city"};
        TypeInformation[] typeInformation = {Types.STRING(),Types.INT(),Types.STRING()};
        TableSource ts = new CsvTableSource("file:///I:/testdata/names.csv",fields,typeInformation);
        tableEnvironment.registerTableSource("names",ts);
        Table table = tableEnvironment.sqlQuery("select * from names where age> 30");
        tableEnvironment.toAppendStream(table, Row.class).print();

        //table sink
//        TableSink sink = new CsvTableSink("file:///i:/testdata/names_result.csv","|");
//        tableEnvironment.registerTableSink("names_result",fields,typeInformation,sink);
//        table.insertInto("names_result");

        //mysql sink
        // 拼接sql
        String upsertSql = "INSERT INTO names_mysql (name,age,city) values (?,?,?) ON DUPLICATE KEY UPDATE " +
                "name=VALUES(name),age=VALUES(age),city=VALUES(city)";
        JDBCAppendTableSink jdbcSink = JDBCAppendTableSink.builder()
                .setDrivername("com.mysql.jdbc.Driver")
                .setDBUrl("jdbc:mysql://192.168.0.110:3306/gmall?characterEncoding=utf8&useSSL=true")
                .setUsername("root")
                .setPassword("mima")
                .setQuery(upsertSql)
                .setParameterTypes(FIELD_TYPES)
                .build();
        tableEnvironment.registerTableSink("names_mysql",fields,typeInformation,jdbcSink);
        table.insertInto("names_mysql");
        env.execute("names");
    }
}
