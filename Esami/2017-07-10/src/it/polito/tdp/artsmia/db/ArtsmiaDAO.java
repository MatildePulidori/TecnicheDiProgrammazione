package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.ArtObjectCount;
import it.polito.tdp.artsmia.model.ArtObjectIdMap;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"),res.getString("classification"), res.getString("title"));
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ArtObject> listObjectsExhibition(ArtObjectIdMap artObjIdMap) {
		
		String sql = "SELECT tab1.object_id, obj1.title, obj1.classification FROM exhibition_objects as tab1, objects as obj1 WHERE tab1.object_id =obj1.object_id";
		
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("title"));
				result.add(artObjIdMap.getObj(artObj));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<ArtObjectCount> getNumberOfExhibitionComuni(ArtObjectIdMap artObjIdMap){
		String sql = "SELECT tab1.object_id, tab2.object_id, count(*) as cnt\n" + 
				"FROM exhibition_objects as tab1, exhibition_objects as tab2 \n" + 
				"WHERE tab1.exhibition_id = tab2.exhibition_id \n" + 
				"AND tab1.object_id < tab2.object_id\n" + 
				"GROUP BY tab1.object_id, tab2.object_id";
		
		List<ArtObjectCount> result = new ArrayList<>();
				
		Connection conn = DBConnect.getConnection();

		try {
				
				PreparedStatement st = conn.prepareStatement(sql);
	
				ResultSet res = st.executeQuery();
	
				while (res.next()) {
					ArtObject o1 = artObjIdMap.get(res.getInt("tab1.object_id"));
					ArtObject o2 = artObjIdMap.get(res.getInt("tab2.object_id"));
					if (o1 != null && o2 != null)
						result.add(new ArtObjectCount(o1, o2, res.getInt("cnt")));
				}
				conn.close();
				return result;
			
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}

	public ArtObject getArtObject(int objId) {

		String sql = "SELECT object_id, classification, title from objects where object_id = ?";
		ArtObject artObj = null;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, objId);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("title"));
				
			}
			conn.close();
			if (artObj == null) {
				new RuntimeException("ArtObject non presente nel Database");
			}
			return artObj;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
