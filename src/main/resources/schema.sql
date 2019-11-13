DROP procedure IF EXISTS plus1inout;

CREATE OR REPLACE FUNCTION plus1inout(IN arg int, OUT res text)
LANGUAGE plpgsql    
AS $$
BEGIN
res := '{"what":"this"}' ;
END;
$$;

select plus1inout(1)

DROP table subscription IF EXISTS;
CREATE TABLE subscription(id IDENTITY, product_name VARCHAR(255), user_id INT);


