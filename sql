/* Lógico_1: */

CREATE TABLE Category (
    ID DOUBLE PRIMARY KEY,
    name CHAR,
    possibleFacets CHAR
);

CREATE TABLE ElasticSearchProduct_Product (
    ID DOUBLE PRIMARY KEY,
    name CHAR,
    price DECIMAL,
    description CHAR,
    sku CHAR,
    category CHAR,
    imageUrl CHAR,
    productAttributeList CHAR,
    quantity INTEGER,
    manufacturer CHAR,
    featured BOOLEAN,
    suggestions CHAR,
    productRating CHAR,
    categoryid INTEGER
);

CREATE TABLE ProductAttribute (
    attributeName CHAR PRIMARY KEY,
    attributeValue CHAR
);

CREATE TABLE ProductRating (
    productId CHAR PRIMARY KEY,
    ratingStars DECIMAL,
    elasticSearchProductId CHAR,
    review CHAR,
    userName CHAR
);

CREATE TABLE ShoppingCartItem (
    name CHAR PRIMARY KEY,
    price DECIMAL
);

CREATE TABLE ShoppingCart_Mail_User_VerificationToken (
    ID CHAR,
    shoppingCartItems CHAR,
    cartTotalPrice DECIMAL,
    numberOfItems INTEGER,
    username CHAR,
    to CHAR,
    from CHAR,
    content CHAR,
    subject CHAR,
    password CHAR,
    passwordConfirmation CHAR,
    enabled BOOLEAN,
    Email CHAR,
    user CHAR,
    token CHAR,
    PRIMARY KEY (ID, to)
);

CREATE TABLE Relacionamento_1 (
    fk_Category_ID DOUBLE,
    fk_Product_ID DOUBLE
);

CREATE TABLE Relacionamento_4 (
    fk_ProductAttribute_attributeName CHAR,
    fk_ElasticSearchProduct_Product_ID DOUBLE
);

CREATE TABLE Relacionamento_5 (
    fk_ElasticSearchProduct_Product_ID DOUBLE,
    fk_ProductRating_productId CHAR
);

CREATE TABLE Relacionamento_6 (
    fk_ProductRating_productId CHAR,
    fk_Mail_User_to CHAR,
    fk_Mail_User_ID CHAR
);

CREATE TABLE Relacionamento_7 (
    fk_ElasticSearchProduct_Product_ID DOUBLE,
    fk_ShoppingCartItem_name CHAR
);

CREATE TABLE Relacionamento_8 (
    fk_ShoppingCartItem_name CHAR,
    fk_ShoppingCart_Mail_User_VerificationToken_ID CHAR
);
 
ALTER TABLE Relacionamento_1 ADD CONSTRAINT FK_Relacionamento_1_1
    FOREIGN KEY (fk_Category_ID)
    REFERENCES Category (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_1 ADD CONSTRAINT FK_Relacionamento_1_2
    FOREIGN KEY (fk_Product_ID)
    REFERENCES ??? (???);
 
ALTER TABLE Relacionamento_4 ADD CONSTRAINT FK_Relacionamento_4_1
    FOREIGN KEY (fk_ProductAttribute_attributeName)
    REFERENCES ProductAttribute (attributeName)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_4 ADD CONSTRAINT FK_Relacionamento_4_2
    FOREIGN KEY (fk_ElasticSearchProduct_Product_ID)
    REFERENCES ElasticSearchProduct_Product (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_5 ADD CONSTRAINT FK_Relacionamento_5_1
    FOREIGN KEY (fk_ElasticSearchProduct_Product_ID)
    REFERENCES ElasticSearchProduct_Product (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_5 ADD CONSTRAINT FK_Relacionamento_5_2
    FOREIGN KEY (fk_ProductRating_productId)
    REFERENCES ProductRating (productId)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_6 ADD CONSTRAINT FK_Relacionamento_6_1
    FOREIGN KEY (fk_ProductRating_productId)
    REFERENCES ProductRating (productId)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_6 ADD CONSTRAINT FK_Relacionamento_6_2
    FOREIGN KEY (fk_Mail_User_to, fk_Mail_User_ID)
    REFERENCES ??? (???);
 
ALTER TABLE Relacionamento_7 ADD CONSTRAINT FK_Relacionamento_7_1
    FOREIGN KEY (fk_ElasticSearchProduct_Product_ID)
    REFERENCES ElasticSearchProduct_Product (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_7 ADD CONSTRAINT FK_Relacionamento_7_2
    FOREIGN KEY (fk_ShoppingCartItem_name)
    REFERENCES ShoppingCartItem (name)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_8 ADD CONSTRAINT FK_Relacionamento_8_1
    FOREIGN KEY (fk_ShoppingCartItem_name)
    REFERENCES ShoppingCartItem (name)
    ON DELETE RESTRICT;
 
ALTER TABLE Relacionamento_8 ADD CONSTRAINT FK_Relacionamento_8_2
    FOREIGN KEY (fk_ShoppingCart_Mail_User_VerificationToken_ID, ???)
    REFERENCES ShoppingCart_Mail_User_VerificationToken (ID, ???)
    ON DELETE RESTRICT;