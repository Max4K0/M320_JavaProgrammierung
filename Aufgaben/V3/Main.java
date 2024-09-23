package V3;

public class Main {
	public static void main(String[] args) {

		Figur axtKrieger = new AxtKrieger();
		Figur dolchMagier = new DolchMagier();
		Figur stabMagier = new StabMagier();


		axtKrieger.setCharakterName("Obama");
		axtKrieger.setWaffenVerhalten(new AxtVerhalten());
		axtKrieger.laufen();
		axtKrieger.kaempfen();

		System.out.println();

		dolchMagier.setCharakterName("Trump");
		dolchMagier.setWaffenVerhalten(new DolchVerhalten());
		dolchMagier.laufen();
		dolchMagier.kaempfen();

		System.out.println();

		stabMagier.setCharakterName("Biden");
		stabMagier.setWaffenVerhalten(new StabVerhalten());
		stabMagier.laufen();
		stabMagier.kaempfen();

		System.out.println();

		stabMagier.setCharakterName("Merkel");
		stabMagier.setWaffenVerhalten(new StabVerhalten());
		stabMagier.laufen();
		stabMagier.kaempfen();
		stabMagier.verwendeWaffe();
	}
}
