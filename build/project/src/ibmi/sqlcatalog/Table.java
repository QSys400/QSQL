package ibmi.sqlcatalog;

public class Table extends SQLCatalogBase {

	Table()
	{ 
		super();
		
		 
		
		this.findLibQuery ="select 1 from QSYS2.SYSTABLES where (upper(TABLE_SCHEMA) =?  or upper(SYSTEM_TABLE_SCHEMA) = ?) and (upper(Table_Name)= $#OBJECTNAME#$ or upper(SYSTEM_TABLE_NAME)=$#OBJECTNAME#$)";
		this.findLibPlaceholderCounter = 2;
		this.objectListQuery =  "Tables@ select Table_Name,SYSTEM_TABLE_Name  from QSYS2.SYSTABLES where (upper(TABLE_SCHEMA)=$#LIBNAME#$  or upper(SYSTEM_TABLE_SCHEMA)= $#LIBNAME#$ )";
	
		
		this.shortSQLQuery.add(0,"Columns@ Select ORDINAL_POSITION as Seq#,COLUMN_NAME,DATA_TYPE,LENGTH,NUMERIC_SCALE from QSYS2.SYSCOLUMNS where (upper(TABLE_SCHEMA) = $#LIBNAME#$ or upper(SYSTEM_TABLE_SCHEMA) = $#LIBNAME#$) and  (upper(Table_Name) = $#OBJECTNAME#$ or upper(SYSTEM_Table_Name) = $#OBJECTNAME#$) order by ORDINAL_POSITION");
		

		
		this.detailSQLQuery.add(0,"Columns@ Select ORDINAL_POSITION,COLUMN_NAME,SYSTEM_COLUMN_NAME,DATA_TYPE,LENGTH,NUMERIC_SCALE,COLUMN_HEADING, COLUMN_TEXT,LONG_COMMENT, IS_NULLABLE,IS_IDENTITY from QSYS2.SYSCOLUMNS where (upper(TABLE_SCHEMA) = $#LIBNAME#$ or upper(SYSTEM_TABLE_SCHEMA) = $#LIBNAME#$) and  (upper(Table_Name) = $#OBJECTNAME#$ or upper(SYSTEM_Table_Name) = $#OBJECTNAME#$) order by ORDINAL_POSITION");
		this.detailSQLQuery.add(1,"Index@ Select INDEX_NAME,SYSTEM_INDEX_NAME,INDEX_SCHEMA,SYSTEM_INDEX_SCHEMA ,IS_UNIQUE,COLUMN_COUNT, INDEX_TEXT, LONG_COMMENT  from QSYS2.SYSINDEXES where (upper(TABLE_SCHEMA) = $#LIBNAME#$ or upper(SYSTEM_TABLE_SCHEMA) = $#LIBNAME#$) and  (upper(Table_Name) = $#OBJECTNAME#$ or upper(SYSTEM_Table_Name) = $#OBJECTNAME#$)");
		this.detailSQLQuery.add(2,"Triggers@ Select TRIGGER_NAME,TRIGGER_SCHEMA,EVENT_MANIPULATION,ACTION_TIMING,EVENT_OBJECT_SCHEMA,EVENT_OBJECT_TABLE,TRIGGER_MODE  from QSYS2.SYSTRIGGERS where (upper(EVENT_OBJECT_SCHEMA) = $#LIBNAME#$ or upper(SYSTEM_EVENT_OBJECT_SCHEMA) = $#LIBNAME#$) and  (upper(EVENT_OBJECT_TABLE) = $#OBJECTNAME#$ or upper(SYSTEM_EVENT_OBJECT_TABLE) = $#OBJECTNAME#$)");
		this.detailSQLQuery.add(3,"Constraint@ Select *  from QSYS2.SYSCST where (upper(TABLE_SCHEMA) = $#LIBNAME#$ or upper(SYSTEM_TABLE_SCHEMA) = $#LIBNAME#$) and  (upper(TABLE_NAME) = $#OBJECTNAME#$ or upper(SYSTEM_TABLE_NAME) = $#OBJECTNAME#$)");
 		
		
	}
	
	
}
