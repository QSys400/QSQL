/*******************************************************************************
 * QSYS400.COM
 * Written by : Sumit goyal
 * 2016
 *******************************************************************************/
package qStatus;

 

public class QLastStatus {

	private static QStatus qStatus= buildStatus();

	private static QStatus buildStatus() {

		 return new QStatus();
 
	}

	public static QStatus get() {
		if (qStatus == null) {
			qStatus = buildStatus();

		}
		return qStatus;
	}
 
	public static void clear()
	{
		QLastStatus.get().clear();
	}

}
