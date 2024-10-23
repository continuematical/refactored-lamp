from cool import model
from django.db import models
from . import constants


class DeletedManager(models.Manager):
    def get_query(self):
        queryset = super(DeletedManager, self).get_queryset()
        return queryset.filter(delete_status=constants.DeleteCode.NORMAL.code)

    def get_all_queryset(self):
        return super(DeletedManager, self).get_queryset()


class BaseModel(model.BaseModel):
    id = models.BigAutoField('主键ID', primary_key=True)
    create_time = models.DateTimeField('创建时间', auto_now_add=True, db_index=True, editable=False)
    modify_time = models.DateTimeField('修改时间', auto_now=True, db_index=True, editable=False)
    delete_status = models.BooleanField('删除状态', choices=constants.DeleteCode.get_choices_list(),
                                        default=constants.DeleteCode.NORMAL.code, null=False, db_index=True)
    remark = models.TextField('备注说明', null=True, blank=True, default='')

    default_manager = models.Manager()
    object = DeletedManager()

    def __str__(self):
        if hasattr(self, 'name'):
            return self.name
        else:
            return super(BaseModel, self).__str__()

    class Meta:
        abstract = True

    def ex_search_fields(self):
        ret = set()
        for field in self._meta.fields:
            if field.name == 'name' and isinstance(field, models.CharField):
                ret.add(field.name)
        return ret

    def get_search_fields(self):
        ret = super(BaseModel, self).get_search_fields()
        return ret.union(self.ex_search_fields())
