package strategy;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class SavingManager implements Saving {
	
	private Saving saving;
	
	public SavingManager(Saving saving) {
		this.saving = saving;
	}

	@Override
	public void save(Object o) throws IOException {
		saving.save(o);

	}


}
