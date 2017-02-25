package ibmi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Result {
	
	private ResultSet rs = null;
	private PreparedStatement preparedStatement = null;
	private  Statement statement = null;
	private String heading="";
	
	public Result(){}
	
	public Result(ResultSet rs,PreparedStatement preparedStatement)
	{
		this.rs=rs;
		this.preparedStatement = preparedStatement;
		
	}
	public Result(ResultSet rs, Statement statement)
	{
		this.rs=rs;
		this.statement = statement;
		
	}
	
	public Result(ResultSet rs, Statement statement, String heading)
	{
		this(rs, statement);
		this.setHeading(heading);
		
	}
	
	
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	
	
	public String getHeading() {
		return (heading==null || heading.isEmpty())?"Result":heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
	
	
	
	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public void reset()
	{
		try
		{
			this.setHeading("");
			if(preparedStatement!=null) preparedStatement.close();
			if(statement!=null) statement.close();
			if(rs!=null) rs.close();
			preparedStatement = null;
			statement = null;
			rs=null;
			
		}
		catch(SQLException sqle){}
	}
}
