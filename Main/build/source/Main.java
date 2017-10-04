import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Main extends PApplet {

Particle[] particles = new Particle[10000];
PImage img;

public void setup( ) {
  
  img = loadImage("logo-imago-400px.png");
  float xoffset = (width - img.width)/2;
  float yoffset = (height - img.height)/2;
  for (int i = 0; i < particles.length; i++) {
    float x = random(width);
    float y = random(height);
    float z = random(30);
    PVector location = new PVector(x, y, z);
    particles[i] = new Particle(location);
    setGoals(particles[i], xoffset, yoffset);
  }
}

public void draw( ) {
  background(100);
  float zDepth = mouseX * 10f / width;
  for (int i = 0; i < particles.length; i++) {
    particles[i].display(zDepth);
  }
}

public void keyPressed() {
  for (int i = 0; i < particles.length; i++) {
    particles[i].run();
  }
}

public void setGoals(Particle p, float xoff, float yoff) {
  float brightness = 0;
  float xImg = 0;
  float yImg = 0;
  while(brightness < 128) {
    xImg = random(img.width);
    yImg = random(img.height);
    brightness = alpha(img.get((int)xImg,(int)yImg));
  }
  float zImg = random(10, 20);
  p.destination = new PVector(xImg + xoff, yImg + yoff, zImg);

}
class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  PVector destination;
  float maxVelocity = 10;
  float dumping = 0.95f;
  int treshhold = 2;

  Particle(PVector loc) {
    location = loc;
    velocity = new PVector();
    acceleration = new PVector();
    destination = new PVector();
  }

  public void update() {
    velocity.add(acceleration);
    velocity.limit(maxVelocity);
    location.add(velocity);
    acceleration = new PVector();
    velocity.mult(dumping);
  }

  public void run() {
    applyForce(chase(2));
    update();
  }

  public void display(float zDepth) {
    if (abs(location.z - zDepth) < treshhold) {
      stroke(255);
      point(location.x,location.y);
    }
  }

  public void applyForce(PVector force) {
    acceleration.add(force);
  }

  public PVector chase(float speed) {
    PVector goalVelocity = PVector.sub(destination, location);
    goalVelocity.setMag(maxVelocity);
    PVector force = PVector.sub(goalVelocity, velocity);
    force.setMag(speed);
    return force;
  }
}
  public void settings() {  size(1000,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
