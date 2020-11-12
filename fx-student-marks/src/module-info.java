module com.jdc.stu {
	
	exports com.jdc.app;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	
	requires lombok;
	
	opens com.jdc.app.entity to javafx.base;
	opens com.jdc.app to javafx.fxml;
	
}