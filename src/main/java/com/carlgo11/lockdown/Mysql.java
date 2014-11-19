package com.carlgo11.lockdown;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mysql {

    public static String url;
    public static String username;
    public static String password;
    public static String database;
    public static String rankstable;
    public static String motdtable;

    public static void updateStrings(String url, String username, String password, String database, String rankstable, String motdtable)
    {
        Mysql.url = url;
        Mysql.username = username;
        Mysql.password = password;
        Mysql.database = database;
        Mysql.rankstable = rankstable;
        Mysql.motdtable = motdtable;
    }

    public static void createTables()
    {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);
            st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS" + Mysql.database + "." + Mysql.motdtable + " (motd text, `only on whitelist` text);");
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static String getMOTD(String Whitelist)
    {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * from " + Mysql.motdtable);
            while (true) {
                if (rs.next()) {
                    if (rs.getString(2).equalsIgnoreCase(Whitelist)) {
                        return rs.getString(1).toString();
                    }
                } else {
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return null;
    }

    public static void setMOTD(String motd)
    {
        Connection con = null;
        Statement st = null;

        try {
            con = DriverManager.getConnection(url + database, Mysql.username, Mysql.password);
            st = con.createStatement();
            PreparedStatement ps = con.prepareStatement("UPDATE " + Mysql.motdtable + " SET `MOTD` = ? WHERE `only on whitelist` = 'false'");
            ps.setString(1, motd);
            ps.execute();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
