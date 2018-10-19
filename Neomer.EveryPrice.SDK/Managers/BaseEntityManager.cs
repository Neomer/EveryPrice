using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Neomer.EveryPrice.SDK.Core;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Session;
using NHibernate;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class BaseEntityManager<TSingleton, TEntity> : Singleton<TSingleton>, IEntityManager
        where TEntity : IEntity
        where TSingleton : class
    {

        protected BaseEntityManager() 
        {
        }

        public virtual IEntity Get(ISession session,  Guid id)
        {
            return session.Get<TEntity>(id);
        }

        public virtual IEnumerable<IEntity> Get(ISession session)
        {
            return session.CreateCriteria<IEntity>().List<IEntity>();
        }

        public virtual void Remove(ISession session,  IEntity entity)
        {
			session.Delete(entity);
        }

        /// <summary>
        /// Сохраняет сущность в БД во внешней транзакции
        /// </summary>
        /// <param name="entity"></param>
        public virtual void Save(ISession session,  IEntity entity)
        {
            if (!(entity is TEntity))
            {
                throw new UnsupportedEntityException();
            }
			session.Save(entity);
        }

        /// <summary>
        /// Сохраняет сущность в БД внутри собственной транзакции
        /// </summary>
        /// <param name="entity"></param>
        public virtual void SaveIsolate(ISession session,  IEntity entity)
        {
            using (var tr = session.BeginTransaction())
            {
                Save(session, entity);
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
