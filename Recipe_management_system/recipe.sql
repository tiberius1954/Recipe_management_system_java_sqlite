BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "categories" (
	"cid"	INTEGER,
	"cname"	TEXT DEFAULT ' ',
	PRIMARY KEY("cid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "documents" (
	"did"	INTEGER,
	"dname"	TEXT DEFAULT ' ',
	"dpath"	TEXT DEFAULT ' ',
	"dext"	TEXT DEFAULT ' ',
	"rid"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("did" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "ingredients" (
	"iid"	INTEGER,
	"iname"	TEXT DEFAULT ' ',
	"uid"	INTEGER,
	"intype"	INTEGER DEFAULT 0,
	PRIMARY KEY("iid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "recipes" (
	"rid"	INTEGER,
	"rname"	TEXT DEFAULT ' ',
	"cid"	INTEGER,
	"instructions"	BLOB DEFAULT ' ',
	"cook_time"	NUMERIC,
	"fid"	INTEGER,
	PRIMARY KEY("rid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "recipes_ingredients" (
	"reinid"	INTEGER,
	"rid"	INTEGER NOT NULL,
	"iid"	INTEGER NOT NULL,
	"uid"	INTEGER NOT NULL,
	"quantity"	NUMERIC NOT NULL,
	PRIMARY KEY("reinid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "unities" (
	"uid"	INTEGER,
	"uname"	TEXT DEFAULT ' ',
	PRIMARY KEY("uid" AUTOINCREMENT)
);
INSERT INTO "categories" ("cid","cname") VALUES (1,'Appetizers
');
INSERT INTO "categories" ("cid","cname") VALUES (2,'Breads');
INSERT INTO "categories" ("cid","cname") VALUES (3,'Fish');
INSERT INTO "categories" ("cid","cname") VALUES (4,'Beef');
INSERT INTO "categories" ("cid","cname") VALUES (6,'Pork');
INSERT INTO "categories" ("cid","cname") VALUES (7,'Soup');
INSERT INTO "categories" ("cid","cname") VALUES (8,'Pastas');
INSERT INTO "categories" ("cid","cname") VALUES (9,'Sides');
INSERT INTO "categories" ("cid","cname") VALUES (10,'Desserts');
INSERT INTO "categories" ("cid","cname") VALUES (16,'Chicken');
INSERT INTO "documents" ("did","dname","dpath","dext","rid") VALUES (2,'beef-wellington.pdf','C:\Users\Zilai Tibor\eclipse-workspace\Recipe_management_system\documents\',' ',12);
INSERT INTO "documents" ("did","dname","dpath","dext","rid") VALUES (3,'one-pan-pasta-with-bacon-and-peas.pdf','C:\Users\Zilai Tibor\eclipse-workspace\Recipe_management_system\documents\',' ',13);
INSERT INTO "documents" ("did","dname","dpath","dext","rid") VALUES (4,'garlic-chicken-fried-chicken.pdf','C:\Users\Zilai Tibor\eclipse-workspace\Recipe_management_system\documents\',' ',14);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (1,'Apple',15,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (2,'Pie Crust',2,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (3,'Flour',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (4,'Sugar',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (5,'Chocolate',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (6,'Butter',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (7,'Chicken',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (8,'Alfredo Sauce',19,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (9,'Pasta',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (10,'Tomato sauce',8,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (11,'Potato',14,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (12,'Onion',15,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (13,'Rice',1,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (14,'Vegetables',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (15,'Milk',1,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (16,'Beef tenderloin',10,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (17,'Mushrooms',1,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (18,'Liver pate',11,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (19,'Pepper',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (20,'Salt',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (21,'Puff pastry',2,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (22,'Egg',15,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (23,'Beef broth',11,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (24,'Red wine',4,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (25,'Bacon',11,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (26,'Garlic',23,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (27,'Chicken broth',1,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (28,'Rotini pasta',11,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (29,'Frozen peas',1,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (30,'Greek yoghurt',24,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (31,'Parmesan cheese',1,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (32,'Black pepper',3,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (33,'Garlic powder',4,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (34,'Paprika',4,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (35,'Bread crumb',1,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (36,'chicken breast halves',15,0);
INSERT INTO "ingredients" ("iid","iname","uid","intype") VALUES (37,'Oil',1,0);
INSERT INTO "recipes" ("rid","rname","cid","instructions","cook_time","fid") VALUES (1,'Apple Pie',1,'Instructions for Apple Pie',45,NULL);
INSERT INTO "recipes" ("rid","rname","cid","instructions","cook_time","fid") VALUES (2,'Chocolate Cake',1,'Instructions for Chocolate Cake',60,NULL);
INSERT INTO "recipes" ("rid","rname","cid","instructions","cook_time","fid") VALUES (3,'Chicken Alfredo Pasta',1,'Instructions for Chicken Alfredo Pasta',30,NULL);
INSERT INTO "recipes" ("rid","rname","cid","instructions","cook_time","fid") VALUES (12,'Beef Wellington',4,'Step 1
Preheat the oven to 425 degrees F (220 degrees C).
Step 2
Place beef tenderloin in a baking dish. Spread 2 tablespoons soft ened butter over beef.
Step 3
Bake in the preheated oven until browned, 10 to 15 minutes. Remove beef from the pan and reservepan juices; allow beef to cool completely.

Step 4
Increase oven temperature to 450 degrees F (230 degrees C).
Step 5
Melt 2 tablespoons butter in a skillet over medium heat. Sauté onion and mushrooms in butter for 5minutes. Remove from heat and let cool.
Step 6
Mix paté and remaining 2 tablespoons soft ened butter together in a bowl; season with salt and pepper.Spread paté mixture over beef. Top with onion and mushroom mixture.
Step 7
Roll out puff pastry dough to about 1/4-inch thickness and place beef in the center.
Step 8
Fold up and seal all the edges, making sure the seams are not too thick. Place beef in a 9x13-inchbaking dish, cut a few slits in the top of dough, and brush with egg yolk.
Step 9
Bake in the preheated oven for 10 minutes. Reduce heat to 425 degrees F (220 degrees C) and continuebaking until pastry is a rich, golden brown, 10 to 15 minutes. An instant-read thermometer inserted intothe center should read between 122 to 130 degrees F (50 to 54 degrees C) for medium rare. Set aside torest.
Step 10
Meanwhile, place reserved pan juices in a small saucepan over high heat. Stir in beef broth and redwine; boil until slightly reduced, about 5 minutes. Strain and serve with beef.



',40,NULL);
INSERT INTO "recipes" ("rid","rname","cid","instructions","cook_time","fid") VALUES (13,'One Pan Pasta with Bacon and Peas',8,'Step 1
Cook bacon in a large skillet over medium heat until crispy, about 3 minutes. Transfer bacon to a platelined with a paper towel. Remove all but about 2 tablespoons bacon grease from the skillet.
Step 2
Add onion to the skillet, and cook until translucent and tender, 3 to 4 minutes. Add garlic and cook untilfragrant, about 30 seconds.
Step 3
Pour in chicken broth and milk, and bring to a boil. Stir in pasta, reduce heat to medium low, cover theskillet, and cook, stirring occasionally, until pasta is al dente, 13 to 15 minutes.
Step 4
Stir in peas, and cook just until thawed. Add Greek yogurt and bacon, stir to combine, and season withsalt and pepper. Sprinkle with Parmesan and serve.',25,NULL);
INSERT INTO "recipes" ("rid","rname","cid","instructions","cook_time","fid") VALUES (14,'Garlic Chicken Fried Chicken',16,'Step 1
In a shallow dish, mix together the garlic powder, pepper, salt, paprika, bread crumbs and flour. In aseparate dish, whisk together the milk and egg.
Step 2
Heat the oil in an electric skillet set to 350 degrees F (175 degrees C). Dip the chicken into the egg andmilk, then dredge in the dry ingredients until evenly coated.
Step 3
Fry chicken in the hot oil for about 5 minutes per side, or until the chicken is cooked through and juicesrun clear. Remove from the oil with a slotted spatula, and serve.

We have determined the nutritional value of oil for frying based on a retention value of 10% aft ercooking. The exact amount may vary depending on cook time and temperature, ingredient density,and the specific type of oil used.',15,NULL);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (27,12,16,10,2.5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (28,12,6,5,6);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (29,12,12,15,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (30,12,17,1,0.5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (31,12,18,11,2);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (32,12,20,3,5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (33,12,19,3,5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (34,12,21,2,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (35,12,22,15,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (36,12,23,11,10.5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (37,12,24,4,2);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (38,13,25,11,8);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (39,13,12,15,0.5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (40,13,26,23,2);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (41,13,27,1,2.66);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (42,13,15,1,0.66);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (43,13,28,11,8);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (44,13,29,1,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (45,13,30,24,0.66);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (46,13,20,3,5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (47,13,19,3,5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (48,13,31,1,0.5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (49,14,33,4,2);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (50,14,32,4,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (51,14,20,5,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (52,14,34,4,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (53,14,35,1,0.5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (54,14,3,1,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (55,14,15,1,0.5);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (56,14,22,15,1);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (57,14,36,15,4);
INSERT INTO "recipes_ingredients" ("reinid","rid","iid","uid","quantity") VALUES (58,14,37,1,1);
INSERT INTO "unities" ("uid","uname") VALUES (1,'Cup');
INSERT INTO "unities" ("uid","uname") VALUES (2,'Package');
INSERT INTO "unities" ("uid","uname") VALUES (3,'g');
INSERT INTO "unities" ("uid","uname") VALUES (4,'Teaspoon');
INSERT INTO "unities" ("uid","uname") VALUES (5,'Tablespoon');
INSERT INTO "unities" ("uid","uname") VALUES (6,'Litre');
INSERT INTO "unities" ("uid","uname") VALUES (7,'Bag');
INSERT INTO "unities" ("uid","uname") VALUES (8,'Milliliter');
INSERT INTO "unities" ("uid","uname") VALUES (9,'Glass');
INSERT INTO "unities" ("uid","uname") VALUES (10,'Pounds');
INSERT INTO "unities" ("uid","uname") VALUES (11,'Ounce');
INSERT INTO "unities" ("uid","uname") VALUES (12,'Pint');
INSERT INTO "unities" ("uid","uname") VALUES (13,'Gallon');
INSERT INTO "unities" ("uid","uname") VALUES (14,'Kg');
INSERT INTO "unities" ("uid","uname") VALUES (15,'Piece');
INSERT INTO "unities" ("uid","uname") VALUES (16,'Slice');
INSERT INTO "unities" ("uid","uname") VALUES (18,'Dag');
INSERT INTO "unities" ("uid","uname") VALUES (19,'ml');
INSERT INTO "unities" ("uid","uname") VALUES (20,'Pairs');
INSERT INTO "unities" ("uid","uname") VALUES (21,'m');
INSERT INTO "unities" ("uid","uname") VALUES (23,'cloves');
INSERT INTO "unities" ("uid","uname") VALUES (24,'cup plain');
COMMIT;
