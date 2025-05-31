-- Drop tables in correct order
drop table if exists items;
drop table if exists categories;

-- Create the categories table
create table categories (
    category_id int primary key auto_increment,
    heading varchar(50) not null,
    description text not null,
    
    constraint headingNotEmpty check(heading <> ''),
    constraint descriptionNotEmpty check(description <> '')
);

-- Insert sample categories with proper descriptions
INSERT INTO categories (heading, description)
VALUES
    ('Appetizers', 'Starter dishes to begin your meal.'),
    ('Main Course', 'Hearty meals to fill you up.'),
    ('Desserts', 'Sweet treats to finish your meal.');

-- Create the items table with a foreign key to categories
create table items (
    item_id int primary key auto_increment,
    category_id int not null,
    name varchar(100) not null,
    description text not null,
    price float(10,2) not null,
    
    constraint nameNotEmpty check(name <> ''),
    constraint descriptionNotEmpty check(description <> ''),
    constraint pricePositive check(price > 0),
    constraint fkCategories 
        foreign key(category_id)
        references categories(category_id)
);

-- Insert menu items with correct category_id
INSERT INTO items (name, description, price, category_id)
VALUES
    ('Caesar Salad', 'Crispy romaine lettuce with Caesar dressing, croutons, and parmesan cheese.', 9.00, 1), -- Appetizers
    ('Spaghetti Carbonara', 'Classic Italian pasta with pancetta, egg, and parmesan cheese.', 15.00, 2), -- Main Course
    ('Chocolate Lava Cake', 'Warm chocolate cake with a gooey molten center.', 7.00, 3); -- Desserts
