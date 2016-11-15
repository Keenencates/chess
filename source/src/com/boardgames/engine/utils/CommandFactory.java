package com.boardgames.engine.utils;

import java.util.HashMap;
import java.util.stream.Collectors;

public class CommandFactory {
	private HashMap<String, Command> commands;
	
	public CommandFactory() {
		commands = new HashMap<>();
	}

	public void addCommand(String name, Command command) {
		getCommands().put(name, command);
	}
	
	public void removeCommand(String name) {
		getCommands().remove(name);
	}
	
	public void executeCommand(String name) {
		if (getCommands().containsKey(name)) {
			getCommands().get(name).apply();
		}
	}

	public void listCommands() {
		System.out.println("Enabled commands: " + getCommands().keySet().stream().collect(Collectors.joining(", ")));
	}

	public HashMap<String, Command> getCommands() {
		return commands;
	}
	
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	public void clearCommands() {
		commands = new HashMap<>();
	}
}
