using Neomer.EveryPrice.SDK.Models;
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
        IEntity Get(Guid id);

        IEnumerable<IEntity> Get();

        void Remove(IEntity entity);

        void Save(IEntity entity);
    }
}
