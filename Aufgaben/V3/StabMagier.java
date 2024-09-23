package V3;

public class StabMagier implements Figur {
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
		System.out.println(charakterName + " läuft mit einem Stab.");
	}

	@Override
	public void kaempfen() {
		System.out.println(charakterName + " kämpft mit Magie und einem Stab.");
	}

	@Override
	public void setWaffenVerhalten(IWaffenVerhalten waffenVerhalten) {

	}

	@Override
	public void verwendeWaffe() {
		System.out.println(charakterName + " verwendet seite Waffe!");
	}
}
