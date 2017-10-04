Particle[] particles = new Particle[10000];
PImage img;

void setup( ) {
  size(1000,1000);
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

void draw( ) {
  background(100);
  float zDepth = mouseX * 10f / width;
  for (int i = 0; i < particles.length; i++) {
    particles[i].display(zDepth);
  }
}

void keyPressed() {
  for (int i = 0; i < particles.length; i++) {
    particles[i].run();
  }
}

void setGoals(Particle p, float xoff, float yoff) {
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
