package hr.fer.zemris.java.hw07.shell;

public interface NameBuilderInfo {

	StringBuilder getStringBuilder(); //returns entire new name after appending all parts

	String getGroup(int index);
}
