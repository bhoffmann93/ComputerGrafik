#version 330
layout(location=0) in vec4 eckenAusJava;
//Matrix aus Java
uniform mat4 rotmat;
uniform mat4 persmat;
layout(location=1) in vec3 farbenAusJava;

//float alpha = 0.3;
out vec3 vcolor;

void main() {
//color
	vcolor = farbenAusJava;
//letzte Koordinate 1 dann punkt (immer in glsl)
	//gl_Position = vec4(rot(eckenAusJava, alpha), 0.0, 1.0);
	gl_Position = persmat * rotmat * eckenAusJava;
}
