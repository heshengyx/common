package com.myself.common.page;

public interface Dialect {
	public static enum Type {  
        MYSQL {  
            public String getValue() {  
                return "mysql";  
            }  
        },  
        MSSQL {  
            public String getValue() {  
                return "sqlserver";  
            }  
        },  
        ORACLE {  
            public String getValue() {  
                return "oracle";  
            }  
        }
	}
	
	String getPageSql(String sql, int offset, int limit);
	String getPageSql(String sql, Page<?> page);
}
