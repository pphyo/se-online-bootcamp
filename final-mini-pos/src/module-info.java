module com.jdc.app {
	
	exports com.jdc.bcmp;
	exports com.jdc.bcmp.views;
	exports com.jdc.bcmp.entity;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires java.sql;
	
	opens com.jdc.bcmp to javafx.fxml;
	opens com.jdc.bcmp.views to javafx.fxml;
}