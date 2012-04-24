package se.cth.hedgehogphoto.Calender;

public class Test {

	public static void main(String[] arg){
		MainModel m = MainModel.getInstance();
		for(int i = 0; i <4;i++){
		m.forwards();
		System.out.print("Månad " + m.getMonth() + "Year " + m.getYear() + "  MaxDays  " + m.getMaxDays());
		System.out.print("Days with picture " + m.getDayswithPicture() + ". ");
	}
	}
}
