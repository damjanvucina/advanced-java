package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	private ILocalizationProvider iLocalizationProvider;
	private boolean connected;

	public LocalizationProviderBridge(ILocalizationProvider iLocalizationProvider) {
		this.iLocalizationProvider = iLocalizationProvider;
	}
	
	public void connect() {
		if(!connected) {
			iLocalizationProvider.addLocalizationListener(() -> fire());
			connected = true;
		}
	}
	
	public void disconnect() {
		if(connected) {
			iLocalizationProvider.removeLocalizationListener(() -> fire());
			connected = false;
		}
	}
	
	public String getString(String key) {
		return iLocalizationProvider.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return iLocalizationProvider.getCurrentLanguage();
	}
}
