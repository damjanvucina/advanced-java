package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		Objects.requireNonNull(l, "Cannot add null listener");
		
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener");
		listeners.remove(l);
	}
	
	public void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}
