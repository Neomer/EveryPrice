using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Neomer.EveryPrice.SDK.Models;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class BaseEntityManager<TEntity> : IEntityManager
        where TEntity : IEntity
    {
        public IEntity Get(Guid id)
        {
            throw new NotImplementedException();
        }

        public void Remove(Guid id)
        {
            throw new NotImplementedException();
        }

        public void Save(Guid id)
        {
            throw new NotImplementedException();
        }
    }
}
