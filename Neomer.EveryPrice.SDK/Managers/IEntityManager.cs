using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public interface IEntityManager
    {
        IEntity Get(Guid id);

        void Remove(Guid id);

        void Save(Guid id);
    }
}
