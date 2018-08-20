package csAsc.com;

public class SQLInjection {
	public static String TransactSQLInjection(String str)
	{
	return str.replaceAll(".*([';]+|(--)+).*", " ");
	}
}
