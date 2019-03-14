package utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static int calcularDiferencaDeMeses(Date dataInicio, Date dataFim) {
		int count = 0;
		if (dataInicio != null && dataFim != null && dataInicio.before(dataFim)) {
			Calendar clStart = Calendar.getInstance();
			clStart.setTime(dataInicio);
			Calendar clEnd = Calendar.getInstance();
			clEnd.setTime(dataFim);
			while (clStart.get(Calendar.MONTH) != clEnd.get(Calendar.MONTH)
					|| clStart.get(Calendar.YEAR) != clEnd.get(Calendar.YEAR)) {
				clStart.add(Calendar.MONTH, 1);
				count++;
			}
		}
		return count;
	}
}
