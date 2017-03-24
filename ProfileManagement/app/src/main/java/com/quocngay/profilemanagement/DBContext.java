package com.quocngay.profilemanagement;

import com.quocngay.profilemanagement.model.AccountModel;
import com.quocngay.profilemanagement.model.ClassModel;
import com.quocngay.profilemanagement.model.SemesterModel;
import com.quocngay.profilemanagement.model.StudentOfClassModel;
import com.quocngay.profilemanagement.model.SubjectModel;
import com.quocngay.profilemanagement.model.SubjectOfClassModel;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ninh huong on 3/24/2017.
 */

public class DBContext {
    public Realm realm;

    public DBContext() {
        realm = Realm.getDefaultInstance();
    }

    private static DBContext inst;

    public static DBContext getInst() {
        if (inst == null) {
            return new DBContext();
        }
        return inst;
    }

    //region AccountModel
    public void addAccountModel(AccountModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxAccountModelId() {
        try{
            return realm.where(AccountModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<AccountModel> getAllAccountModel(){
        return realm.where(AccountModel.class).findAll();
    }

    public AccountModel getAccountModelByID(int id) {
        return realm.where(AccountModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleAccountModel(int id) {
        final AccountModel result = realm.where(AccountModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllAccountModel() {
        final RealmResults<AccountModel> result = realm.where(AccountModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion

    //region ClassModel
    public void addClass(ClassModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxClassId() {
        try{
            return realm.where(ClassModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<ClassModel> getAllClass(){
        return realm.where(ClassModel.class).findAll();
    }

    public ClassModel getClassByID(int id) {
        return realm.where(ClassModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleClass(int id) {
        final ClassModel result = realm.where(ClassModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllClass() {
        final RealmResults<ClassModel> result = realm.where(ClassModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion

    //region SemesterModel
    public void addSemesterModel(SemesterModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxSemesterModelId() {
        try{
            return realm.where(SemesterModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<SemesterModel> getAllSemesterModel(){
        return realm.where(SemesterModel.class).findAll();
    }

    public SemesterModel getCurrentSemesterModel(){
        Date today = new Date();
        return realm.where(SemesterModel.class).lessThanOrEqualTo("startDate", today).
                greaterThanOrEqualTo("endDate", today).findAll().first();
    }

    public SemesterModel getSemesteModelByID(int id) {
        return realm.where(SemesterModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleSemesterModel(int id) {
        final SemesterModel result = realm.where(SemesterModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllSemesterModel() {
        final RealmResults<SemesterModel> result = realm.where(SemesterModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion

    //region SubjectModel
    public void addSubjectModel(SubjectModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxSubjectModelId() {
        try{
            return realm.where(SubjectModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<SubjectModel> getAllSubjectModel(){
        return realm.where(SubjectModel.class).findAll();
    }

    public SubjectModel getSubjectModelByID(int id) {
        return realm.where(SubjectModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleSubjectModel(int id) {
        final SubjectModel result = realm.where(SubjectModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllSubjectModel() {
        final RealmResults<SubjectModel> result = realm.where(SubjectModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion

    //region SubjectOfClassModel
    public void addSubjectOfClassModel(SubjectOfClassModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxSubjectOfClassModelId() {
        try{
            return realm.where(SubjectOfClassModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<SubjectOfClassModel> getSubjectOfClassModelsBySemesterId(int id){
        return realm.where(SubjectOfClassModel.class).equalTo("classModel.semesterModel.id", id).findAll();
    }

    public List<SubjectOfClassModel> getAllSubjectOfClassModel(){
        return realm.where(SubjectOfClassModel.class).findAll();
    }

    public SubjectOfClassModel getSubjectOfClassModelByID(int id) {
        return realm.where(SubjectOfClassModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleSubjectOfClassModel(int id) {
        final SubjectOfClassModel result = realm.where(SubjectOfClassModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllSubjectOfClassModel() {
        final RealmResults<SubjectOfClassModel> result = realm.where(SubjectOfClassModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion

    //region StudentOfClassModel
    public void addStudentOfClass(StudentOfClassModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxStudentOfClassId() {
        try{
            return realm.where(StudentOfClassModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<StudentOfClassModel> getAllStudentOfClass(){
        return realm.where(StudentOfClassModel.class).findAll();
    }

    public List<StudentOfClassModel> getStudentOfClassesBySubOfClassId(int id){
        return realm.where(StudentOfClassModel.class).equalTo("subjectOfClassModel.id", id).findAll();
    }

    public StudentOfClassModel getStudentOfClassByID(int id) {
        return realm.where(StudentOfClassModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleStudentOfClass(int id) {
        final StudentOfClassModel result = realm.where(StudentOfClassModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllStudentOfClass() {
        final RealmResults<StudentOfClassModel> result = realm.where(StudentOfClassModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion
}
