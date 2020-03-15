package exception;

import java.sql.*;

/**
 * java 1.7 新特性
 *
 * @author 孙继峰
 * @date 2019/06/25
 */
public class TryWithResourceTest {

    /**
     * 语法糖 TryWithResource
     * 可以关闭实现 AutoCloseable 和 Closeable 接口的资源
     */
    public void jdbc() {
        String query = "SELECT * FROM dual";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=GMT");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将以上方法反编译后
     * <p>
     * 在 try with resource 中定义的顺序 Connection -> Statement -> ResultSet
     * 关闭顺序为                        ResultSet -> Statement -> Connection
     */
    public void decompile() {
        String var1 = "SELECT * FROM dual";

        try {
            Connection var2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=GMT");

            try {
                Statement var3 = var2.createStatement();

                try {
                    ResultSet var4 = var3.executeQuery(var1);

                    try {
                        while (var4.next()) {
                            String var5 = var4.getString("name");
                            System.out.println(var5);
                        }
                    } catch (Throwable var10) {
                        if (var4 != null) {
                            try {
                                var4.close();
                            } catch (Throwable var9) {
                                var10.addSuppressed(var9);
                            }
                        }

                        throw var10;
                    }

                    if (var4 != null) {
                        var4.close();
                    }
                } catch (Throwable var11) {
                    if (var3 != null) {
                        try {
                            var3.close();
                        } catch (Throwable var8) {
                            var11.addSuppressed(var8);
                        }
                    }

                    throw var11;
                }

                if (var3 != null) {
                    var3.close();
                }
            } catch (Throwable var12) {
                if (var2 != null) {
                    try {
                        var2.close();
                    } catch (Throwable var7) {
                        var12.addSuppressed(var7);
                    }
                }

                throw var12;
            }

            if (var2 != null) {
                var2.close();
            }
        } catch (SQLException var13) {
            var13.printStackTrace();
        }
    }

}
