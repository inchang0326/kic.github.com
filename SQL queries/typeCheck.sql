-- This statement helps check the type of input parameters.

-- 1. Decalre a variable and store the number of the attribute type to be examined. 
DECLARE @chkPoint AS INT;
SELECT @chkPoint = SYSTEM_TYPE_ID FROM SYS.ALL_COLUMNS
WHERE OBJECT_ID = OBJECT_ID('table_name') AND NAME = 'attribute_name';

-- 2. Compare the one with the input parameter type 
if CONVERT(VARCHAR(MAX), SQL_VARIANT_PROPERTY(@parameter_name, 'BaseType')) <> TYPE_NAME(@chkPoint)
BEGIN
	RAISERROR('Data validation error! Please check your input datatype.', 16, 1);	
	RETURN;
END
