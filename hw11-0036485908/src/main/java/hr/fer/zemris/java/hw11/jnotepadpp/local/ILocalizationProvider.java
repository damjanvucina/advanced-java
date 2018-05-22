package hr.fer.zemris.java.hw11.jnotepadpp.local;

public interface ILocalizationProvider {

	String getString(String key);
	void addLocalizationListener(ILocalizationListener l);
	void removeLocalizationListener(ILocalizationListener l);
}
