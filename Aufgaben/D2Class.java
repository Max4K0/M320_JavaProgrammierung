import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Java Dokumentation fuer aufgabe D2
 * @author Max
 * @version 1.0.0
 *
 */

class Test {
	private int points;

	/**
	 *  Konstruktor
	 * @param points Punkte, welche in der Pruefung erreicht wurden.
	 */

	public Test(int points) {
		this.points = points;
	}

	/**
	 * Berechnet mir die Note von den Punkten.
	 * @return Note
	 */
	public float calculateGrade() {

		if (points >= 90) {
			return 6.0f;
		} else if (points >= 80) {
			return 5.0f;
		} else if (points >= 50) {
			return 4.0f;
		} else if (points >= 40) {
			return 3.0f;
		} else if (points >= 30) {
			return 2.0f;
		} else {
			return 1.0f;
		}
	}
}

/**
 * Klasse Student sind die schueler mit namen und testen.
 */

class Student {
	private String name;
	private List<Test> tests;


	/**
	 * getter
	 * @return gibt den Namen vom schueler
	 */

	public String getName() {
		return name;
	}

	/**
	 * setter
	 * @param name setzt den Name vom schueler
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Konstruktor
	 * @param name Der name vom Schueler sowie die tests
	 */

	public Student(String name) {
		this.name = name;
		this.tests = new ArrayList<>();
	}

	/**
	 * Schueler schreibt eine Pruefung.
	 * @param test Die Pruefung.
	 */
	public void addTest(Test test) {
		this.tests.add(test);
	}


	/**
	 * Berechnet den Notendurchschnitt.
	 * @return Notendurchschnitt eines Schuelers.
	 */
	public float getNotenschnitt() {
		if (tests.isEmpty()) {
			return 0.0f;
		}

		float totalGrades = 0;
		for (Test test : tests) {
			totalGrades += test.calculateGrade();
		}
		return totalGrades / tests.size();
	}
}

/**
 * Das ist eine Schulkrasse.
 */
class Class {
	private List<Student> students;

	/**
	 * Konstruktor
	 */
	public Class() {
		this.students = new ArrayList<>();
	}

	/**
	 * Fuegt einen Schueler zur Klasse hinzu.
	 * @param s Das ist der hinzugefuegte Schueler
	 */
	public void addStudent(Student s) {
		if (students.size() < 28) {
			this.students.add(s);
		} else {
			System.out.println("Klasse ist voll");
		}
	}

	/**
	 * Berechnet den Notendurchschnitt der gesamten Klasse.
	 * @return Notendurchschnitt der klasse.
	 */
	public float getAverage() {
		if (students.isEmpty()) {
			return 0.0f;
		}

		float totalAverage = 0;
		for (Student student : students) {
			totalAverage += student.getNotenschnitt();
		}
		return totalAverage / students.size();
	}



}

/**
 * Main
 */

public class D2Class {
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

/**
 * Teste werden hier Manuel hinzugefuegt
 */

		Test test1 = new Test(85);
		Test test2 = new Test(18);
		Test test3 = new Test(92);

		/**
		 * Studenten werden hier Manuel hinzugefuegt.
		 */

		Student student1 = new Student("Obama");
		Student student2 = new Student("ObamaDerZweite");
		Student student3 = new Student("ObamaDerDritte");

		/**
		 * Studenten schreiben hier Teste.
		 */
		student3.addTest(test3);
		student2.addTest(test2);
		student1.addTest(test1);

/**
 * Studenten werden einer klasse zugewiesen.
 */

		Class class1 = new Class(); //klasse 5b
		class1.addStudent(student1);
		class1.addStudent(student2);
		class1.addStudent(student3);


		/**
		 * Abfrage, ob ein Schueler hinzugefuegt werden soll.
		 */
		System.out.println("Neuen Schueler hinzufuegen? 1 = Ja | 2 = Nein");
		if(in.nextInt() == 1) {
			System.out.println("Name des Schuelers: ");
			Student student4 = new Student(in.next());
			System.out.println("Wie viele Punkte hatte der Schueler?: (100 = Note 6)");
			Test test4 = new Test(in.nextInt());
			student4.addTest(test4);

			class1.addStudent(student4);
			System.out.println("Durchschnittsnote von " + student4.getName() + ": " + student4.getNotenschnitt());
		}


/**
 * Gibt Daten der Klasse und von den Studenten aus.
 */

		System.out.println("Durchschnittsnote von " + student2.getName()+ ": " + student2.getNotenschnitt());
		System.out.println("Durchschnittsnote der Klasse: " + class1.getAverage());
	}
}
