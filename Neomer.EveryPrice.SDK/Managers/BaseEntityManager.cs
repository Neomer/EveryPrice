using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Neomer.EveryPrice.SDK.Core;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;

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

        public virtual void Remove(IEntity entity)
        {
            NHibernateHelper.Instance.CurrentSession.Delete(entity);
        }

        public virtual void Save(IEntity entity)
        {
            NHibernateHelper.Instance.CurrentSession.SaveOrUpdate(entity);
        }
    }
}
