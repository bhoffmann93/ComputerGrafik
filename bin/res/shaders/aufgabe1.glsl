#version 330

out vec3 pixelFarbe;
vec2 pixelXY = gl_FragCoord.xy;

void square(vec2 a, vec2 b) {

	//vec2 a = vec2(100.0*2, 100.0*2);
	//vec2 b = vec2(600.0*2, 600.0*2);

	//pixelFarbe = vec3(0.0, 1.0, 1.0);
	
	if (pixelXY.x  >= a.x && pixelXY.y >= a.y &&
		pixelXY.x  <= b.x && pixelXY.y <= b.y ) {
		pixelFarbe = vec3(1.0, 0.0, 0.0);	
	}

}

void circle(float r, vec2 m) {
	//pixelFarbe = vec3(0.0, 0.0, 0.5);
	//vec2 m = vec2(350*2, 350*2);
		
	if(distance (pixelXY, m) <= r) {
		pixelFarbe = vec3(1.0, 0.0, 0.0);
		}
}

void rotsquare (vec2 a, vec2 b, float ang) {
	mat2 rm = mat2 (cos(ang), -sin(ang), sin(ang), cos(ang));
    pixelXY = pixelXY*rm;
    
	if (pixelXY.x  >= a.x && pixelXY.y >= a.y &&
		pixelXY.x  <= b.x && pixelXY.y <= b.y ) {
		pixelFarbe = vec3(1.0, 1.0, 0.0);	
	}
	
	
}

void main() {
    pixelFarbe = vec3(0.0, 0.0, 0.0);
    
	square(vec2(100*2, 100*2), vec2(200*2, 200*2));
	square(vec2(600*2, 600*2), vec2(650*2, 650*2));
	circle(100.0, vec2(350*2, 350*2));
	circle(50.0, vec2(500.0*2 ,600.0*2));
	
	rotsquare(vec2(300.0*2, 300.0*2), vec2(400.0*2, 400.0*2), 0.1);

	
}