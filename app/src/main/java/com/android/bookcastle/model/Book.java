package com.android.bookcastle.model;


public class Book {
        private String id;
        private String title;
        private String author;
        private String description;
        private String image;

        public Book(String id, String title, String author, String description, String image) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.description = description;
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", description='" + description + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
}
