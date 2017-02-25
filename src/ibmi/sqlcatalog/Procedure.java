package ibmi.sqlcatalog;

public class Procedure extends SQLCatalogBase {

	Procedure()
	{ 
		super();
		
	 
		
		
		this.findLibQuery ="select 1 from QSYS2.SYSPROCS where (upper(ROUTINE_SCHEMA) =?  or upper(SPECIFIC_SCHEMA) = ?) and (upper(ROUTINE_NAME)= $#OBJECTNAME#$ or upper(SPECIFIC_NAME)=$#OBJECTNAME#$)";
		this.findLibPlaceholderCounter = 2;
		this.objectListQuery =  "Tables@ select ROUTINE_NAME,SPECIFIC_NAME  from QSYS2.SYSPROCS where (upper(ROUTINE_SCHEMA)=$#LIBNAME#$  or upper(SPECIFIC_SCHEMA)= $#LIBNAME#$ )";
	
		
		this.shortSQLQuery.add(0,"Basic Details@ Select ROUTINE_BODY,EXTERNAL_NAME,EXTERNAL_LANGUAGE,RESULT_SETS from QSYS2.SYSPROCS where (upper(ROUTINE_SCHEMA) = $#LIBNAME#$ or upper(SPECIFIC_SCHEMA) = $#LIBNAME#$) and  (upper(ROUTINE_NAME) = $#OBJECTNAME#$ or upper(SPECIFIC_NAME) = $#OBJECTNAME#$)");
		this.shortSQLQuery.add(1,"Parameters@ Select ORDINAL_POSITION as Seq#,PARAMETER_MODE,PARAMETER_NAME,DATA_TYPE from QSYS2.SYSPARMS A JOIN QSYS2.SYSPROCS B "
				+ "				on A.SPECIFIC_SCHEMA = B.SPECIFIC_SCHEMA and A.SPECIFIC_NAME = B.SPECIFIC_NAME "
				+ "				where (upper(B.ROUTINE_SCHEMA) = $#LIBNAME#$ or upper(B.SPECIFIC_SCHEMA) = $#LIBNAME#$) and  (upper(B.ROUTINE_NAME) = $#OBJECTNAME#$ or upper(B.SPECIFIC_NAME) = $#OBJECTNAME#$) ");
		

		
		this.detailSQLQuery.add(0,"Basic Details@ Select * from QSYS2.SYSPROCS where (upper(ROUTINE_SCHEMA) = $#LIBNAME#$ or upper(SPECIFIC_SCHEMA) = $#LIBNAME#$) and  (upper(ROUTINE_NAME) = $#OBJECTNAME#$ or upper(SPECIFIC_NAME) = $#OBJECTNAME#$)");
		
		this.detailSQLQuery.add(1,"Parameters@ Select * from QSYS2.SYSPARMS A JOIN QSYS2.SYSPROCS B "
				+ "				on A.SPECIFIC_SCHEMA = B.SPECIFIC_SCHEMA and A.SPECIFIC_NAME = B.SPECIFIC_NAME "
				+ "				where (upper(B.ROUTINE_SCHEMA) = $#LIBNAME#$ or upper(B.SPECIFIC_SCHEMA) = $#LIBNAME#$) and  (upper(B.ROUTINE_NAME) = $#OBJECTNAME#$ or upper(B.SPECIFIC_NAME) = $#OBJECTNAME#$) ");
		
		
	}
	
	
}
