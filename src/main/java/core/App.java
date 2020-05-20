package core;

public class App {

	public static void main(String[] args) {
		System.out.println("java -cp ./target/HW_42_new-1.0.jar core.Firefox; cat ./report_01.csv | sed 's/,/ ,/g' | column -t -s, | less -S");

	}

}
