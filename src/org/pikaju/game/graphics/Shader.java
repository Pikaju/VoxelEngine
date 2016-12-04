package org.pikaju.game.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL20;
import org.pikaju.game.util.math.Vec3;

public class Shader {

	private int program = -1;
	
	public Shader(String vertexShaderFile, String fragmentShaderFile) {
		String vertexSource = loadShaderSource(vertexShaderFile);
		String fragmentSource = loadShaderSource(fragmentShaderFile);
		
		int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		GL20.glShaderSource(vertexShader, vertexSource);
		GL20.glShaderSource(fragmentShader, fragmentSource);
		
		GL20.glCompileShader(vertexShader);
		GL20.glCompileShader(fragmentShader);
		
		if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == 0) {
			System.out.println(vertexShaderFile + ":\n" + GL20.glGetShaderInfoLog(vertexShader, 1024));
		}
		if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == 0) {
			System.out.println(fragmentShaderFile + ":\n" + GL20.glGetShaderInfoLog(fragmentShader, 1024));
		}
		
		program = GL20.glCreateProgram();
		GL20.glAttachShader(program, vertexShader);
		GL20.glAttachShader(program, fragmentShader);
		
		GL20.glLinkProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0) {
			System.out.println(vertexShaderFile + " + " + fragmentShaderFile + " linking:\n" + GL20.glGetProgramInfoLog(program, 1024));
		}
		GL20.glValidateProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0) {
			System.out.println(vertexShaderFile + " + " + fragmentShaderFile + " validating:\n" + GL20.glGetProgramInfoLog(program, 1024));
		}
		
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
	}
	
	public void enable() {
		GL20.glUseProgram(program);
	}
	
	public void disable() {
		GL20.glUseProgram(0);
	}
	
	public void setUniform(String name, Vec3 value) {
		GL20.glUniform3f(GL20.glGetUniformLocation(program, name), value.getX(), value.getY(), value.getZ());
	}

	public void setUniform(String name, float value) {
		GL20.glUniform1f(GL20.glGetUniformLocation(program, name), value);
	}
	
	public void setUniform(String name, int value) {
		GL20.glUniform1i(GL20.glGetUniformLocation(program, name), value);
	}
	
	private static final String loadShaderSource(String filePath) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Shader.class.getResourceAsStream(filePath)));
			String line = "";
			while((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
