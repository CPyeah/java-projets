package org.cp.reportTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateWeeklyReportTemplate {

	public static void main(String[] args) {
		new CreateWeeklyReportTemplate().create(LocalDate.of(2022,10,8));
	}

	public void create(LocalDate start) {
		List<List<LocalDate>> allDateList = new ArrayList<>();
		LocalDate end = start.plusDays(31);
		for (LocalDate i = start; i.compareTo(end) < 0; ) {
			List<LocalDate> week = new ArrayList<>();
			if (i.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
				week.add(LocalDate.of(i.getYear(), i.getMonth(), i.getDayOfMonth()));
				i = i.plusDays(1);
			}
			while (!i.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
				week.add(LocalDate.of(i.getYear(), i.getMonth(), i.getDayOfMonth()));
				i = i.plusDays(1);
			}
			allDateList.add(week);
		}
		System.out.println(allDateList.size());
		System.out.println("-------------------------------------------");

		// output
		System.out.println("# 周报-" + start.format(DateTimeFormatter.ofPattern("yyyy年MM月")));
		System.out.println();
		for (int i = 0; i < allDateList.size(); i++) {
			List<LocalDate> week = allDateList.get(i);
			System.out.println("## " + week_th.get(i));
			for (LocalDate day : week) {
				System.out.println("### " +
						day.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
						+ "("
						+ dayOfWeekStringMap.get(day.getDayOfWeek())
						+ ")");
				System.out.println("1. ");
				System.out.println();
			}
			System.out.println("### 总结");
			System.out.println("1. ");
			System.out.println("---");
			System.out.println();
		}

		System.out.println("-------------------------------------------");
	}

	private final Map<Integer, String> week_th = Map
			.of(0, "第一周",
					1, "第二周",
					2, "第三周",
					3, "第四周",
					4, "第五周"
			);
	private final Map<DayOfWeek, String> dayOfWeekStringMap = Map.of(
			DayOfWeek.MONDAY, "星期一",
			DayOfWeek.TUESDAY, "星期二",
			DayOfWeek.WEDNESDAY, "星期三",
			DayOfWeek.THURSDAY, "星期四",
			DayOfWeek.FRIDAY, "星期五",
			DayOfWeek.SATURDAY, "星期六",
			DayOfWeek.SUNDAY, "星期天"
	);

}
