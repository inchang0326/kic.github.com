-- This is a USP code to work the type check statements.

USE database_name;
GO

IF object_id('usp_name') IS NOT NULL
DROP PROC usp_name;
GO

CREATE PROC usp_name
			@s_id AS INT,-- primary key
			@s_name AS VARCHAR(50)
AS
BEGIN 
	DECLARE @chkPoint AS INT;
	SELECT @chkPoint = SYSTEM_TYPE_ID FROM SYS.ALL_COLUMNS
	WHERE OBJECT_ID = OBJECT_ID('database_name') AND NAME = 's_id';
	if CONVERT(VARCHAR(MAX), SQL_VARIANT_PROPERTY(@s_id, 'BaseType')) <> TYPE_NAME(@chkPoint)
	BEGIN
		RAISERROR('Data validation error! Please check your input datatype.', 16, 1);
		RETURN;
	END

	DECLARE @chkPoint2 AS INT;
	SELECT @chkPoint2 = SYSTEM_TYPE_ID FROM SYS.ALL_COLUMNS
	WHERE OBJECT_ID = OBJECT_ID('dbo.Store') AND NAME = 's_name';
	if CONVERT(VARCHAR(MAX), SQL_VARIANT_PROPERTY(@s_name, 'BaseType')) <> TYPE_NAME(@chkPoint2)
	or @s_name is null
	BEGIN
		RAISERROR('Data validation error! Please check your input datatype.', 16, 1);
		RETURN;
	END

	UPDATE dbo.Store
	SET s_name = @s_name
	WHERE s_id = @s_id; 
END;
GO
