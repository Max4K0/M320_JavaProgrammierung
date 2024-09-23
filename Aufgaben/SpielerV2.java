import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpielerV2 {
	protected String name;

	public SpielerV2(String name) {
		this.name = name;
	}

	public void zeigeName() {
		System.out.println("Name: " + name);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);


		Goalie goalie = new Goalie("Hans", 1.90);
		Angreifer angreifer1 = new Angreifer("Tim");
		Angreifer angreifer2 = new Angreifer("Jens");
		Angreifer angreifer3 = new Angreifer("Jonas");
		Angreifer angreifer4 = new Angreifer("Josef");
		Angreifer angreifer5 = new Angreifer("Paul");
		Angreifer angreifer6 = new Angreifer("Alex");
		Angreifer angreifer7 = new Angreifer("Noah");
		Angreifer angreifer8 = new Angreifer("Ben");
		Angreifer angreifer9 = new Angreifer("Leon");
		Angreifer angreifer10 = new Angreifer("Luca");
		Angreifer angreifer11 = new Angreifer("Elias");
		Angreifer angreifer12 = new Angreifer("Finn");
		Angreifer angreifer13 = new Angreifer("Felix");
		Angreifer angreifer14 = new Angreifer("Jonas");
		Angreifer angreifer15 = new Angreifer("Julian");
		Angreifer angreifer16 = new Angreifer("Erik");

		Verteidiger verteidiger1 = new Verteidiger("Jasmine");
		Verteidiger verteidiger2 = new Verteidiger("Ash");
		Verteidiger verteidiger3 = new Verteidiger("Obama");
		Verteidiger verteidiger4 = new Verteidiger("Trump");

		List<Angreifer> angreiferListe = new ArrayList<>();
		angreiferListe.add(angreifer1);
		angreiferListe.add(angreifer2);
		angreiferListe.add(angreifer3);
		angreiferListe.add(angreifer4);
		angreiferListe.add(angreifer5);
		angreiferListe.add(angreifer6);
		angreiferListe.add(angreifer7);
		angreiferListe.add(angreifer8);
		angreiferListe.add(angreifer9);
		angreiferListe.add(angreifer10);
		angreiferListe.add(angreifer11);
		angreiferListe.add(angreifer12);
		angreiferListe.add(angreifer13);
		angreiferListe.add(angreifer14);
		angreiferListe.add(angreifer15);
		angreiferListe.add(angreifer16);



		List<Verteidiger> verteidigerListe = new ArrayList<>();
		verteidigerListe.add(verteidiger1);
		verteidigerListe.add(verteidiger2);
		verteidigerListe.add(verteidiger3);
		verteidigerListe.add(verteidiger4);

		Mannschaft teamX = new Mannschaft(goalie, angreiferListe, verteidigerListe);

		System.out.println("Neuen Spieler erstellen: 1->Goalie 2->Angreifer 3->Verteidiger 0-Kein neuer Spieler");
		int art = in.nextInt();

		switch (art) {
			case 1:
				teamX.setGoalie(Menu.createGoalie());
				break;
			case 2:
				teamX.addAngreifer(Menu.createAngreifer());
				break;
			case 3:
				teamX.addVerteidiger(Menu.createVerteidiger());
				break;
			default:

		}
		teamX.zeigeTeam();

		System.out.println("Torwart Körpergrösse: ");
				goalie.zeigeKoepergroesse();
		angreifer1.joyTraining();
		angreifer14.joyTraining();
	}
}

class Goalie extends SpielerV2 {
	private double koerpergroesse;

	public Goalie(String name) {
		super(name);
	}

	public Goalie(String name, double koerpergroesse) {
		super(name);
		this.koerpergroesse = koerpergroesse;
	}

	public void zeigeKoepergroesse() {
		System.out.println("Körpergrösse: " + koerpergroesse);
	}
}

class Angreifer extends SpielerV2 {
	public Angreifer(String name) {
		super(name);
	}

	public void joyTraining() {
		System.out.println(name + " hat spass beim Trainieren.");
	}
}

class Verteidiger extends SpielerV2 {
	public Verteidiger(String name) {
		super(name);
	}
}

class Mannschaft {
	private Goalie goalie;
	private List<Angreifer> angreifer;
	private List<Verteidiger> verteidiger;

	public Mannschaft(Goalie goalie, List<Angreifer> angreifer, List<Verteidiger> verteidiger) {
		this.goalie = goalie;
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
	}

	public void zeigeTeam() {
		System.out.println("Mannschaft:");
		goalie.zeigeName();
		for (Angreifer a : angreifer) {
			a.zeigeName();
		}
		for (Verteidiger v : verteidiger) {
			v.zeigeName();
		}
	}

	public void setGoalie(Goalie goalie) {
		this.goalie = goalie;
	}

	public void addAngreifer(Angreifer angreifer) {
		this.angreifer.add(angreifer);
	}

	public void addVerteidiger(Verteidiger verteidiger) {
		this.verteidiger.add(verteidiger);
	}
}

class Menu {
	public static Goalie createGoalie() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gebe den Namen und die grösse vom Goalie ein: ");
		return new Goalie(in.next(), in.nextDouble());
	}

	public static Angreifer createAngreifer() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gebe den Namen vom Angreifer ein: ");
		return new Angreifer(in.next());
	}

	public static Verteidiger createVerteidiger() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gebe den Namen vom Verteidiger ein: ");
		return new Verteidiger(in.next());
	}
}
