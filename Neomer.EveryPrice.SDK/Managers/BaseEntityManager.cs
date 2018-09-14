using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Neomer.EveryPrice.SDK.Core;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Exceptions.Managers;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class BaseEntityManager<TSingleton, TEntity> : Singleton<TSingleton>, IEntityManager
        where TEntity : IEntity
        where TSingleton : class
    {
        protected BaseEntityManager() 
        {
        }

        public virtual IEntity Get(Guid id)
        {
            return NHibernateHelper.Instance.CurrentSession.Get<TEntity>(id);
        }

        public virtual IEnumerable<IEntity> Get()
        {
            return NHibernateHelper.Instance.CurrentSession.CreateCriteria<IEntity>().List<IEntity>();
        }

        public virtual void Remove(IEntity entity)
        {
            NHibernateHelper.Instance.CurrentSession.Delete(entity);
        }

        /// <summary>
        /// Сохраняет сущность в БД во внешней транзакции
        /// </summary>
        /// <param name="entity"></param>
        public virtual void Save(IEntity entity)
        {
            if (!(entity is TEntity))
            {
                throw new UnsupportedEntityException();
            }
            NHibernateHelper.Instance.CurrentSession.Save(entity);
        }

        /// <summary>
        /// Сохраняет сущность в БД внутри собственной транзакции
        /// </summary>
        /// <param name="entity"></param>
        public virtual void SaveIsolate(IEntity entity)
        {
            using (var tr = NHibernateHelper.Instance.CurrentSession.BeginTransaction())
            {
                Save(entity);
                try
                {
                    tr.Commit();
                }
                catch (Exception ex)
                {
                    tr.Rollback();
                    throw ex;
                }
            }
        }
    }
}
