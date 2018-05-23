package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
	private static final String BASE_NAME = "hr.fer.zemris.java.hw11.jnotepadpp.local.translations";

	private String language;
	private ResourceBundle bundle;
	private static LocalizationProvider instance = new LocalizationProvider();

	private LocalizationProvider() {
		setLanguage("en");
	}

	public static LocalizationProvider getInstance() {
		return instance;
	}

	public void setLanguage(String language) {
		this.language = language;

		setUpBundle();
	}

	private void setUpBundle() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BASE_NAME, locale);
		
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
