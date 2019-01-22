package ab3;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {

    private ShaderProgram shaderProgram;
    private ShaderProgram secondObject;
    private Matrix4 rotmat = new Matrix4();
    private Matrix4 rotmat2 = new Matrix4();
    private Matrix4 persmat = new Matrix4();
    private float angle;
    private float[] pyramid;
    private float[] cube;
    private float[] uvkoordiaten;
    private float[] uvkoordinatenCube;
    private float[] farben;
    private float[] normalen;
    private float[] normalenCube;
    private Texture tex0;
    private Texture tex1;
    private Texture tex2;
    private int textureIdCube;
    private int vaoId2;
    private int textureIDPyramid;
    private int textureIDPyramid2;
    private int vaoId1;



    public static void main(String[] args) {
        new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
    }

    @Override
    protected void init() {

        shaderProgram = new ShaderProgram("aufgabe3");
        secondObject = new ShaderProgram("cube");

        createArrays();

        initializePyramid();
        initializeCube();

        rotmat = new Matrix4();
        rotmat2 = new Matrix4();
        persmat = new Matrix4(1f, 100f);
        // Matrix an Shader �bertragen (muss nur einmal übertragen werden nicht die ganze Zeit)
        int persloc = glGetUniformLocation(shaderProgram.getId(), "persmat");
        glUniformMatrix4fv(persloc, false, persmat.getValuesAsArray());

        glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
        glEnable(GL_CULL_FACE); // backface culling aktivieren

    }

    private void initializePyramid() {
        glUseProgram(shaderProgram.getId());

        //Vertices
        vaoId1 = glGenVertexArrays();
        glBindVertexArray(vaoId1);

        attachVBO(pyramid,0,3);
        //Colors zum VAO hinzufügen
        attachVBO( farben, 1, 3);
        //UV Koordinaten zum VAO hinzufügen
        attachVBO( uvkoordiaten, 2, 2);
        //normals zum VAO
        attachVBO( normalen,3,3);

        //Texture (muss 16x16/512x512...)
        tex0 = new Texture("wall.jpg",8,true);
        tex1 = new Texture("floor.png", 8, true);
        textureIDPyramid = tex0.getId();
        textureIDPyramid2 = tex1.getId();
        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glBindTexture(GL_TEXTURE_2D, textureIDPyramid);
        glBindTexture(GL_TEXTURE_2D, textureIDPyramid2);


    }

    private void initializeCube() {
        //cube
        glUseProgram(secondObject.getId());

        vaoId2 = glGenVertexArrays();
        glBindVertexArray(vaoId2);

        attachVBO(cube, 0, 3);
        attachVBO(uvkoordinatenCube, 1, 3);
        attachVBO(normalenCube, 2, 2);

        tex2 = new Texture("wall_small.jpg",8,true);
        textureIdCube = tex2.getId();
        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glBindTexture(GL_TEXTURE_2D, textureIdCube);
    }



    //Methode um VBO zum VAO hinzuzuf�gen
    private void attachVBO( float[] array, int num, int vectype) {
        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, array, GL_STATIC_DRAW);
        glVertexAttribPointer(num, vectype, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(num);
    }

    private void createArrays() {
        // Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
/*		float [] dreiecksKoordinaten = new float [] {
				0.0f, 0.2f,
			   -0.3f, -0.3f,
				0.3f, -0.3f
		};*/

        //Pyramiden Koordinaten -> gegen Uhrzeigersinn
        //für jede fläche (triangle) JEWEILS drei punkte
        pyramid = new float[]{
                //Sides
                0.1f, -0.1f, -0.1f, // B
                -0.1f, -0.1f, -0.1f, // A
                0f, 0.1f, 0f, // Spitze

                -0.1f, -0.1f, -0.1f, // A
                -0.1f, -0.1f, 0.1f, // D
                0f, 0.1f, 0f, // Spitze

                -0.1f, -0.1f, 0.1f, // D
                0.1f, -0.1f, 0.1f, // C
                0f, 0.1f, 0f, // Spitze

                0.1f, -0.1f, 0.1f, // C
                0.1f, -0.1f, -0.1f, // B
                0f, 0.1f, 0f, // Spitze
                //Boden
                -0.1f, -0.1f, -0.1f, // A
                0.1f, -0.1f, 0.1f, // C
                -0.1f, -0.1f, 0.1f, // D

                0.1f, -0.1f, -0.1f, // B
                0.1f, -0.1f, 0.1f, // C
                -0.1f, -0.1f, -0.1f // A

        };

        //UV Koordinaten
        uvkoordiaten = new float[]{
                //Sides
                1, 1,   //B
                0, 1,   //A
                0, 0,   //Spitze

                1, 1,   //A
                0, 1,   //D
                0, 0,   //Spitze

                1, 1,   //D
                0, 1,   //C
                0, 0,   //Spitze

                1, 1,   //C
                0, 1,   //B
                0, 0,   //Spitze

                //Boden
                0, 0,   //A
                0.3f, 0.3f,   //C
                0.3f, 0,   //D

                0, 0.3f,   //B
                0.3f, 0.3f,   //C
                0, 0    //A
        };

        //für alle dreieckspunkte ne farbe
        farben = new float[]{
                1.0f, 0.0f, 0.0f, //r
                0.0f, 1.0f, 0.0f, //g
                0.0f, 0.0f, 1.0f, //b

                1.0f, 0.0f, 0.0f, //r
                0.0f, 1.0f, 0.0f, //g
                0.0f, 0.0f, 1.0f, //b

                1.0f, 0.0f, 0.0f, //r
                0.0f, 1.0f, 0.0f, //g
                0.0f, 0.0f, 1.0f, //b

                1.0f, 0.0f, 0.0f, //r
                0.0f, 1.0f, 0.0f, //g
                0.0f, 0.0f, 1.0f, //b

                1.0f, 0.0f, 0.0f, //r
                0.0f, 1.0f, 0.0f, //g
                0.0f, 0.0f, 1.0f, //b

                1.0f, 0.0f, 0.0f, //r
                0.0f, 1.0f, 0.0f, //g
                0.0f, 0.0f, 1.0f //b
        };

        //Normalen
        normalen = getNormals(pyramid);



        cube = new float[] {
                // vorne
                -0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F,
                0.5F, 0.5F,

                // hinten
                -0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F,

                -0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F,

                // unten
                -0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F,

                -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F,

                // oben
                -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F,

                -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F,

                // links
                -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, 0.5F,

                -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, -0.5F,

                // rechts
                0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, -0.5F,

                0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, -0.5F };

        uvkoordinatenCube = new float[]{
                //front
                0, 1,
                1, 1,
                0, 0,

                0, 0,
                1, 1,
                1, 0,

                //back
                1, 1,
                1, 0,
                0, 1,

                1, 0,
                0, 0,
                0, 1,

                //bottom
                0, 0,
                1, 1,
                1, 0,

                0, 0,
                0, 1,
                1, 1,

                //top
                0, 1,
                1, 1,
                1, 0,

                0, 1,
                1, 0,
                0, 0,

                //left
                1, 0,
                0, 0,
                1, 1,

                1, 1,
                0, 0,
                0, 1,

                //right
                0, 0,
                0, 1,
                1, 0,

                0, 1,
                1, 1,
                1, 0
        };


        normalenCube = getNormals(cube);
    }

    private float[] getNormals(float[] coordinates){
        Vector3[] vectors = new Vector3[coordinates.length/3];
        int count = 0;
        for (int i = 0; i < coordinates.length; i+=3) {
            Vector3 a = new Vector3(coordinates[i],coordinates[i+1],coordinates[i+2]);
            vectors[count] = a;
            count++;
        }

        float[] normals = new float[coordinates.length];
        int ncount = -1;
        for (int i = 0; i < vectors.length; i+=3) {
            Vector3 a = vectors[i];
            Vector3 b = vectors[i+1];
            Vector3 c = vectors[i+2];

            Vector3 normA = a.between(b).cross(a.between(c));
            normA.normalize();
            for (int j = 0; j < 3; j++) {
                normals[++ncount]=normA.x();
                normals[++ncount]=normA.y();
                normals[++ncount]=normA.z();
            }
        }
        return normals;
    }

    @Override
    public void update() {
        // Transformation durchf�hren (Matrix anpassen)
        angle += 1f;
        //matrix immer wieder mit einheitsmatrix überschreiben
        //rotmat = dummer Name
        rotmat = new Matrix4();
        rotmat.scale(3f).rotateZ(angle).rotateY(angle).translate(0, 0, -1.5f);
        //rotateZ(angle)

        rotmat2 = new Matrix4();
        rotmat2.scale(3f).rotateZ(angle).rotateY(angle).translate(0, 0, -1.5f);
    }

    @Override
    protected void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Matrix an Shader übertragen
        int loc = glGetUniformLocation(shaderProgram.getId(), "rotmat");
        glUniformMatrix4fv(loc, false, rotmat.getValuesAsArray());

        // VAOs zeichnen
        // 18 mal shader aufrufen da pyramid 18 xyz Koordinatenpaare
        glBindVertexArray(vaoId1);
        glBindTexture(GL_TEXTURE_2D, tex0.getId());
        glDrawArrays(GL_TRIANGLES, 0, 12);
        glBindTexture(GL_TEXTURE_2D, tex1.getId());
        glDrawArrays(GL_TRIANGLES, 12, 6);

       //cube
        int loc2 = glGetUniformLocation(secondObject.getId(), "rotmat2");
        glUniformMatrix4fv(loc2, false,rotmat2.getValuesAsArray());
        glBindVertexArray(vaoId2);
        glBindTexture(GL_TEXTURE_2D, textureIdCube);
        glDrawArrays(GL_TRIANGLES,0,36);
    }

    @Override
    public void destroy() {
    }
}
