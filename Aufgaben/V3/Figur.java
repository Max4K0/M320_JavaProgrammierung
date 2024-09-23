package V3;

public interface Figur extends IWaffenVerhalten {

		String getCharakterName();
		void setCharakterName(String charakterName);
		void laufen();
		void kaempfen();
		void setWaffenVerhalten(IWaffenVerhalten waffenVerhalten);

}

