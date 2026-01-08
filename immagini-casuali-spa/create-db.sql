CREATE TABLE IF NOT EXISTS images (
  id INT AUTO_INCREMENT PRIMARY KEY,
  image_url VARCHAR(256),
  description TEXT,
  rating INT
);
