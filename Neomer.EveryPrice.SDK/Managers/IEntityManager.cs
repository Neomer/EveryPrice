using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Session;
using NHibernate;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public interface IEntityManager
    {
        IEntity Get(ISession session, Guid id);

        IEnumerable<IEntity> Get(ISession session);

        void Remove(ISession session,  IEntity entity);

        void Save(ISession session,  IEntity entity);
    }
}
