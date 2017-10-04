class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  PVector destination;
  float maxVelocity = 10;
  float dumping = 0.95;
  int treshhold = 2;

  Particle(PVector loc) {
    location = loc;
    velocity = new PVector();
    acceleration = new PVector();
    destination = new PVector();
  }

  void update() {
    velocity.add(acceleration);
    velocity.limit(maxVelocity);
    location.add(velocity);
    acceleration = new PVector();
    velocity.mult(dumping);
  }

  void run() {
    applyForce(chase(2));
    update();
  }

  void display(float zDepth) {
    if (abs(location.z - zDepth) < treshhold) {
      stroke(255);
      point(location.x,location.y);
    }
  }

  void applyForce(PVector force) {
    acceleration.add(force);
  }

  PVector chase(float speed) {
    PVector goalVelocity = PVector.sub(destination, location);
    goalVelocity.setMag(maxVelocity);
    PVector force = PVector.sub(goalVelocity, velocity);
    force.setMag(speed);
    return force;
  }
}
