package algo.com.mmchords;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DataRepository {

    FirebaseFirestore firestore;

    public DataRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public void searchSongsByTitle(String title) {
        firestore.collectionGroup(Constants.KEY_SONGS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null || task.getResult().isEmpty()) {
                                //empty
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //document.detData
                                    //document.getId()
                                }
                            }
                        } else {
                            //error
                        }
                    }
                });
    }

}
