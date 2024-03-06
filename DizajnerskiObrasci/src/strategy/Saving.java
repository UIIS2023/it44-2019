package strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

public interface Saving {

	void save(Object o) throws IOException;
}
