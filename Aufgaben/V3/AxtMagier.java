package V3;

public class AxtMagier implements Figur {
	private String charakterName;

	@Override
	public String getCharakterName() {
		return charakterName;
	}

	@Override
	public void setCharakterName(String charakterName) {
		this.charakterName = charakterName;
	}

	@Override
	public void laufen() {
		System.out.println(charakterName + " läuft mit einer Axt.");
	}

	@Override
	public void kaempfen() {
		System.out.println(charakterName + " kämpft mit Magie und einer Axt.");
	}

	@Override
	public void setWaffenVerhalten(IWaffenVerhalten waffenVerhalten) {

	}

	@Override
	public void verwendeWaffe() {
		System.out.println(charakterName + " verwendet seite Waffe!");
	}
}
